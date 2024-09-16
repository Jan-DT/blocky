[Blocky]: https://github.com/Jan-DT/blocky/
[wiki]: https://github.com/Jan-DT/blocky/wiki/
[docs]: https://jan-dt.github.io/blocky/
[new-issue]: https://github.com/Jan-DT/blocky/issues/new/
[support]: https://github.com/Jan-DT/blocky/discussions/
[Minestom]: https://github.com/minestom/Minestom

> [!CAUTION]
> Blocky is currently very unstable, and every update *will* break stuff.
> For this reason, we highly recommend only using Blocky for experimental purposes.

> [!NOTE]
> As Blocky is currently in early development, we would love any feedback and contributions.
> Leave your suggestions and issues at our [issues][new-issue] page, or [contribute](#contributing) yourself.

<div align="center">

# <a name="top"></a>🧱 Blocky

Blocky is an (experimental) engine for building Minecraft game servers from scratch.\
[**Read the wiki ⟶**][wiki]

[Report a Bug][new-issue] ⋅ [Get Support][support] ⋅ [Reference][docs]

</div>

---

## About the project

Blocky is meant to be like a game engine for Minecraft servers, built on top of [Minestom].
Where Minestom tries to be as hands-off as possible (which is what makes it so cool),
Blocky tries to provide a complete, and sometimes opinionated,
toolset for building entire games in Minecraft.

The main goal of Blocky is to make the experience of building games as simple and seamless as possible.
To achieve this, we aim to provide a game engine-like architecture inspired by Unity and Godot,
with many common features like animation, path-finding and maybe someday even a visual editor.

At this point in time, Blocky is still very much in the planning phase,
and every update might (and probably will) include many large breaking changes.
This means that you probably shouldn't be using Blocky to build any projects yet.

Some features that could possibly, one day, be added:

#### Engine
- [ ] Fully functional object-trait (component) system
- [ ] Migrate to using just Minestom scratch
- [ ] Remove all dependencies on Minestom from Core
- [ ] Serialization system for objects and resources
- [ ] Animation system
    - Simplified API for particle animations
- [ ] Pathfinding system
- [ ] AI system (something like Goal-oriented Action Planning)
- [ ] Implementations for common things like:
    - Custom entity models, using projects like [WSEE](https://github.com/AtlasEngineCa/WorldSeedEntityEngine)
    - Custom resource pack support (for things like UI)
    - Persistent data storage
    - Support for autoscaling

#### Ecosystem
- [ ] CLI tool for project/file generation
- [ ] A visual editor client mod?

Please note that these are just some random ideas. Let me know your feedback!

### Project Structure

The project is currently structured as follows:

- [`engine/`](./engine) - contains everything related to the engine itself
  - [`engine/core`](./engine/core) - contains core interfaces and classes which the engine builds on
  - [`engine/impl`](./engine/impl) - contains standard implementations on top of the core functionality
- [`examples/`](./examples) - contains examples showcasing engine functionality, and proving the API

Client functionality is currently only available on the [feat/experimental-client](https://github.com/Jan-DT/blocky/tree/feat/experimental-client) branch.

<p align="right">(<a href="#top">back to top</a>)</p>

## Contributing

Do you have any ideas, issues or do you think something should be done differently?
Then open an [issue][new-issue], or contribute yourself!

To contribute to this project, fork it, make your changes and open a pull request.
Big feature changes, additions and ideas should be discussed through an [issue][new-issue] first.

Read more about contributing [here](CONTRIBUTE.md).

### Credits

This project would not be possible without:
- The [Minestom] project, especially its [contributors](https://github.com/Minestom/Minestom/graphs/contributors).

### License

This project is licensed under EITHER OF

- Apache license, version 2.0 (available at [LICENSE-APACHE](./LICENSE-APACHE) or http://www.apache.org/licenses/LICENSE-2.0)
- MIT license (available at [LICENSE-MIT](./LICENSE-MIT) or https://opensource.org/license/MIT)

at your option.

<p align="right">(<a href="#top">back to top</a>)</p>
