declare namespace JS_TESTS {
    type Nullable<T> = T | null | undefined
    function KtSingleton<T>(): T & (abstract new() => any);
    namespace foo {
        abstract class TestEnumClass {
            private constructor();
            static get A(): foo.TestEnumClass & {
                get name(): "A";
                get ordinal(): 0;
            };
            static get B(): foo.TestEnumClass & {
                get name(): "B";
                get ordinal(): 1;
            };
            get name(): "A" | "B";
            get ordinal(): 0 | 1;
            get constructorParameter(): string;
            get foo(): number;
            bar(value: string): string;
            bay(): string;
            static values(): Array<foo.TestEnumClass>;
            static valueOf(value: string): foo.TestEnumClass;
        }
        /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
        namespace TestEnumClass.$metadata$ {
            const constructor: abstract new () => TestEnumClass;
        }
        namespace TestEnumClass {
            class Nested {
                constructor();
                get prop(): string;
            }
            /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
            namespace Nested.$metadata$ {
                const constructor: abstract new () => Nested;
            }
        }
        class OuterClass {
            constructor();
        }
        /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
        namespace OuterClass.$metadata$ {
            const constructor: abstract new () => OuterClass;
        }
        namespace OuterClass {
            abstract class NestedEnum {
                private constructor();
                static get A(): foo.OuterClass.NestedEnum & {
                    get name(): "A";
                    get ordinal(): 0;
                };
                static get B(): foo.OuterClass.NestedEnum & {
                    get name(): "B";
                    get ordinal(): 1;
                };
                get name(): "A" | "B";
                get ordinal(): 0 | 1;
                static values(): Array<foo.OuterClass.NestedEnum>;
                static valueOf(value: string): foo.OuterClass.NestedEnum;
            }
            /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
            namespace NestedEnum.$metadata$ {
                const constructor: abstract new () => NestedEnum;
            }
        }
    }
}