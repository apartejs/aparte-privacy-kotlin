// The runtime and the model (int4, ~114 KB): finds passwords, API keys, tokens and private keys
// in text. No dependency but the Kotlin standard library.

plugins {
    kotlin("jvm")
}

description = "Finds personal data (names, addresses, emails, phone numbers, URLs, dates, account numbers) in text, on-device: the aparte-privacy runtime with its model (int8, ~196 KB) bundled"
