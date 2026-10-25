# aparte-privacy-kotlin

Finds personal data in text on Android and the JVM: names, addresses, emails, phone numbers, URLs, dates and account numbers. The [aparte-privacy](https://huggingface.co/apartejs/aparte-privacy) model (196 KB, int8) is bundled. Offline, no dependency but the Kotlin standard library.

```kotlin
implementation("dev.apartejs:privacy:1.1.0")   // build.gradle.kts

val privacy = Privacy.load()   // load once, share across threads
privacy.detect("My name is John Smith and my email is john.smith@gmail.com")
// [Span(start=11, end=21, text=John Smith, subtype=private_person, score=1.0),
//  Span(start=38, end=58, text=john.smith@gmail.com, subtype=private_email, score=1.0)]
```

Android 5.0 and later, any JVM 8 or later, usable from Java (`Privacy.load()`, `detect(text)`). Offsets are UTF-16 indices: `text.substring(span.start, span.end)`. The model reports raw spans; `minScore`, `ignore` (exact values) and `ignorePatterns` (regexes) are your policy, applied after it.

It is the same runtime as the JavaScript package [`@aparte/privacy`](https://www.npmjs.com/package/@aparte/privacy) and gives the same spans as the Python reader, checked text by text.

## Limits

- Names are the weakest category: 0.77 F1 on our test set and 0.85 on short messages, against 0.9 and more for the others. Plan for a human check on names where it matters.
- A product or service name can be taken for a name, above all next to a key (« ma clé Slack : … ») or at the start of a message. Use `ignore` for the ones you know.
- Public figures and fictional characters are masked too. Use `ignore` for the names you want to keep.
- Short messages were the weak spot of model v1 (names F1 0.35 on 1,067 short chat messages); v1.1 reaches 0.85 there.
- English and French only, as tested.

## Tests

```bash
./gradlew test                                              # API tests
APARTE_PRIVACY_REFS=references_int8.jsonl ./gradlew test    # + equality with the reference spans
```

The reference file is on [Hugging Face](https://huggingface.co/apartejs/aparte-privacy/tree/main): it holds personal-data-shaped text, so it is not in this repository.

## Versions

All aparte-privacy packages share one version, `<model version>.<package fix>`: 1.1.x carries model v1.1.

## License

MIT, Copyright (c) 2026 Paul Richez.
