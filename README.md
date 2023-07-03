![logo](./frontend/public/icon-192.png)

[![CI](https://github.com/axelrindle/spel-debugger/actions/workflows/ci.yml/badge.svg)](https://github.com/axelrindle/spel-debugger/actions/workflows/ci.yml)
[![GitHub release (release name instead of tag name)](https://img.shields.io/github/v/release/axelrindle/spel-debugger?label=version)](https://github.com/axelrindle/spel-debugger/pkgs/container/spel-debugger)

# Debugger for SpEL

This application is a debugger for the
[Spring Expression Language (SpEL)](https://docs.spring.io/spring-framework/reference/core/expressions.html).

## Installation

It's easiest to install the application using Docker:

```shell
docker run -d \
  --name spel-debugger \
  -p 8080:8080 \
  ghcr.io/axelrindle/spel-debugger:latest
```

## Security Considerations

The application does not provide any authentication mechanisms. As the SpEL is quite powerful and will execute
arbitrary Java code, harm could be done by unauthorized users. Consider installing an authentication
middleware such as HTTP Basic.

Read more on this topic here:

- https://0xn3va.gitbook.io/cheat-sheets/framework/spring/spel-injection#spel-injection
- https://xvnpw.medium.com/hacking-spel-part-1-d2ff2825f62a

## Development

### Requirements

- JDK 17
- Node.js 18

### Backend

1. Open the project in your favorite IDE (I prefer IntelliJ)
2. Fire up the `SpelDebuggerApplication` class.
3. The api url is http://localhost:8080/.
   If you'd like to use Swagger, go to http://localhost:8080/swagger-ui/index.html

### Frontend

1. cd into the `frontend` directory
2. Run `npm ci`
3. Run `npm run dev`
4. Open http://localhost:5173/

## License

[GPLv3](LICENSE)

## Attribution

Spring is a [registered trademark](https://spring.io/trademarks) of Pivotal Software, Inc.

[Icon](https://icon.kitchen/i/H4sIAAAAAAAAAz1QMW7DMAz8C7t6SOA6BbwG7dShaLIVHWiJtoXSpitLSQPDfy%2Bl1tl4h%2BPxjgtckCPNUC9g0X%2BdexoI6uAjFdB259ukCAy7CX2AApruKCxeuYdDheXOKqcy%2BglKnabn14zf0Fo3dsk0yAT1fleAd12vojQ2EoIMfzNTm9k17b3IqGCBFgfHN3V8F9VKuitst1guIDsDdYs8U967Z8KSDmWVMxz%2FM6vdTCmdpRYjpxLOyKjEbByNhkAtmu7UY646f0fnDVPumnpFr7T22Ea44kX%2FVYDZjpZPj%2FtqD6v6DGIjp29%2BwJUa%2BFx%2FAf%2FBQ9pgAQAA)
created using [IconKitchen](https://icon.kitchen/).