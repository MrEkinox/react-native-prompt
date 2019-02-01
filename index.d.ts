// Type definitions for react-native-prompt 0.3.1
// Project: https://github.com/mrekinox/react-native-prompt
// Definitions by: Krystof Celba <https://github.com/krystofcelba>
// TypeScript Version: 2.6.1

type PromptButton = {
  text?: string;
  onPress?: (message: string) => void;

  /** @platform ios */
  style?: 'default' | 'cancel' | 'destructive';
};

type PromptType = 'default' | 'plain-text' | 'secure-text';
type PromptTypeIOS = 'login-password';
type PromptTypeAndroid = 'numeric' | 'email-address' | 'phone-pad';

type PromptStyleAndroid = 'default' | 'shimo';

type KeybordTypeIOS = 'default' | 'email-address' | 'numeric' | 'phone-pad' | 'ascii-capable' | 'numbers-and-punctuation' | 'url', 'number-pad' | 'name-phone-pad' | 'decimal-pad' | 'twitter' | 'web-search';

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
    
  /** @platform ios */
  keyboardType?: KeybordTypeIOS;

  /** @platform android */
  placeholder?: string;

  /** @platform android */
  cancelable?: boolean;

  /** @platform android */
  style?: PromptStyleAndroid;
}

declare function prompt(
  title?: string,
  message?: string,
  callbackOrButtons?: ((value: string) => void) | Array<PromptButton>,
  options?: PromptOptions,
): void;

export default prompt;
