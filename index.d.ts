// Type definitions for react-native-prompt-android 0.3.1
// Project: https://github.com/shimohq/react-native-prompt-android
// Definitions by: Krystof Celba <https://github.com/krystofcelba>
// TypeScript Version: 2.6.1

import { KeyboardTypeOptions } from "react-native";

export interface PromptButton {
    text?: string;
    onPress?: (text?: string) => void;

    /** @platform ios */
    style?: "default" | "cancel" | "destructive";
}

type PromptType = "default" | "plain-text" | "secure-text";
type PromptTypeIOS = "login-password";
type PromptTypeAndroid = "numeric" | "email-address" | "phone-pad";

export interface PromptOptions {
    /**
     * * Cross platform:
     *
     * - `'default'`
     * - `'plain-text'`
     * - `'secure-text'`
     *
     * * iOS only:
     *
     * - `'login-password'`
     *
     * * Android only:
     *
     * - `'numeric'`
     * - `'email-address'`
     * - `'phone-pad'`
     */
    type?: PromptType | PromptTypeIOS | PromptTypeAndroid;

    defaultValue?: string;

    /** @platform android */
    placeholder?: string;

    /** @platform android */
    cancelable?: boolean;

    /** @platform android */
    color?: string;

    /** @platform ios */
    keyboardType?: KeyboardTypeOptions;
}

declare function prompt(
    title?: string,
    message?: string,
    buttons?: PromptButton[],
    options?: PromptOptions
): void;

export default prompt;
