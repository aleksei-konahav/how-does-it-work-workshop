### Implementations Pros and Cons

|Implementation|Pros|Cons|
|---|---|---|
|Direct implementation in methods|Simple and straightforward <br/> Easy to understand (no magic)|To reuse needs copy-pasting <br/> "Main" logic sits with "infrastructure" logic|
|Common logic in methods|Still simple and straightforward <br/> Less overhead with method invocation <br/> Easy to reuse|Need to have several method signatures to support all possible cases|
|AOP with method names pointcut|Clear separation of "business" logic and cross-cutting concerns <br/> Modular codebase which is easy to change <br/> Easy to extend and add to more places by changing pointcut <br/> Business logic could be tested withou aspect logic|Too magic <br/> Need additional effort to test aspect with main logic|
|AOP with annotation for pointcut|The same as above <br/> Now it's clear that method has "additional" behaviour|The same as above|