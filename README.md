# aparte-privacy-kotlin

Finds personal data in text on Android and the JVM: names, addresses, emails, phone numbers, URLs, dates and account numbers. The [aparte-privacy](https://huggingface.co/apartejs/aparte-privacy) model (196 KB, int8) is bundled. Offline, no dependency but the Kotlin standard library.

```kotlin
implementation("dev.apartejs:privacy:1.0.0")   // build.gradle.kts

val privacy = Privacy.load()   // load once, share across threads
privacy.detect("Appelle Marie Lambert au 06 12 34 56 78")
// [Span(start=8, end=20, text=Marie Lambert, subtype=private_person, score=…), …]
```

Android 5.0 and later, any JVM 8 or later, usable from Java (`Privacy.load()`, `detect(text)`). Offsets are UTF-16 indices: `text.substring(span.start, span.end)`. The model reports raw spans; `minScore`, `ignore` (exact values) and `ignorePatterns` (regexes) are your policy, applied after it.

It is the same runtime as the JavaScript package [`@aparte/privacy`](https://www.npmjs.com/package/@aparte/privacy) and gives the same spans as the Python reader, checked text by text.

## Limits

- Names are the weakest category: about 0.66 F1 on our test set, against 0.9 and more for the others. Plan for a human check on names where it matters.
- Public figures and fictional characters are masked too. Use `ignore` for the names you want to keep.
- English and French only, as tested.

## Tests

```bash
./gradlew test                                              # API tests
APARTE_PRIVACY_REFS=references_int8.jsonl ./gradlew test    # + equality with the reference spans
```

The reference file is on [Hugging Face](https://huggingface.co/apartejs/aparte-privacy/tree/main): it holds personal-data-shaped text, so it is not in this repository.

## Versions

All aparte-privacy packages share one version, `<model version>.<package fix>`: 1.0.x carries model v1.0.

## License

MIT, Copyright (c) 2026 Paul Richez.
