import { Alert } from "react-native";
import { PromptOptions, PromptButton } from ".";

export default function prompt(
    title?: string,
    message?: string,
    buttons?: PromptButton[],
    options?: PromptOptions
): void {
    const Dialog: any = Alert;
    Dialog.prompt(
        title,
        message,
        buttons,
        options["type"] || "default",
        options["defaultValue"],
        options["keyboardType"]
    );
}
