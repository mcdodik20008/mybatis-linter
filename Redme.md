# MyBatis Linter

MyBatis Linter is a [Detekt](https://github.com/detekt/detekt) plugin that analyzes MyBatis mapper files in Kotlin projects.
It loads the mapper XML, generates all possible SQL variants and applies custom rules to them.

## Building

Use Gradle to build the project:

```bash
./gradlew build
```

Run unit tests with:

```bash
./gradlew test
```

## SQL rules and variant generation

`SqlRuleSetProvider` registers SQL inspection rules. The included example `NoSelectAllRule` reports usages of `SELECT *` in mapper methods.
`SqlVariantGenerator` and `SqlVariantFlattener` read mapper XML and produce permutations of SQL based on conditional tags. The maximum number of variants can be configured via the system property `sqlVariantMax` (defaults to `9000`).

## Contributing

Contributions are welcome! Feel free to open issues or pull requests. Please make sure tests pass before submitting changes.