ischemia
========

Bootstrap Scheme interpreter written in Java, following [Peter Michaux's articles](http://michaux.ca/articles/scheme-from-scratch-introduction).

Not terribly many things are supported, since the interpreter is only supposed to be sturdy enough to run the initial compiler. The following is the dump of the environment that shows all the primitive procedures available:

`car apply quotient string->number set-car! char->integer symbol? eq? char? string?`
`input-port? remainder close-input-file null? integer->char write read`
`interaction-environment output-port? pair? cons open-input-file procedure?`
`eof-object? symbol->string error eval write-char * + null-environment list`
`string->symbol cdr open-output-file - read-char set-cdr! environment number->string`
`peek-char integer? boolean? close-output-file load > = <`

Floating point is not supported, neither are macros, vectors and many other features that make Scheme Scheme. There are some basic procedures for accessing environments; there are two branches available that represent variable bindings as a Java HashMap and a set of LISP lists. The first one doesn't allow any manipulation of environments, but is supposedly faster; the second one is the opposite. I'm not entirely sure whether one option is better than the other, so I'm keeping both branches.

I managed to run the [metacircular evaluator](http://michaux.ca/articles/scheme-from-scratch-bootstrap-conclusion) on both versions.

It also has tail call optimization (unlike Java) via trampolines.

Error handling is minimal; giving things less arguments than they want or differently typed things from what they want is a sure way to a Java stack trace or an ambiguous error message passed via one of the only two existing exceptions.
