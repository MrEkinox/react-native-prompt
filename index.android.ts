import { NativeModules } from "react-native";
const PromptAndroid = NativeModules.PromptAndroid;
import { PromptOptions, PromptButton } from ".";

export default function prompt(
    title: string,
    message?: string,
    buttons?: PromptButton[],
    options?: PromptOptions
): void {

    let config: any = {
        title: title || "",
        message: message || ""
    };

    if (options) {
        config = {
            ...config,
            cancelable: options.cancelable !== false,
            type: options.type || "default",
            defaultValue: options.defaultValue || "",
            placeholder: options.placeholder || ""
        };
    }
    // At most three buttons (neutral, negative, positive). Ignore rest.
    // The text 'OK' should be probably localized. iOS Alert does that in native.
    const validButtons: PromptButton[] = buttons
        ? buttons.slice(0, 3)
        : [{ text: "OK" }];
    const buttonPositive = validButtons.pop();
    const buttonNegative = validButtons.pop();
    const buttonNeutral = validButtons.pop();

    if (buttonNeutral) {
        config = { ...config, buttonNeutral: buttonNeutral.text || "" };
    }
    if (buttonNegative) {
        config = { ...config, buttonNegative: buttonNegative.text || "" };
    }
    if (buttonPositive) {
        config = {
            ...config,
            buttonPositive: buttonPositive.text || ""
        };
    }

    PromptAndroid.promptWithArgs(config, (action, buttonKey, input) => {
        if (action !== PromptAndroid.buttonClicked) {
            return;
        }
        if (buttonKey === PromptAndroid.buttonNeutral) {
            buttonNeutral.onPress && buttonNeutral.onPress(input);
        } else if (buttonKey === PromptAndroid.buttonNegative) {
            buttonNegative.onPress && buttonNegative.onPress();
        } else if (buttonKey === PromptAndroid.buttonPositive) {
            buttonPositive.onPress && buttonPositive.onPress(input);
        }
    });
}
