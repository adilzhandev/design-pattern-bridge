# Assignment 3 — Bridge Pattern

| | |
|---|---|
| Student | Adilzhan Kuandykov |
| Group | SE-2526 |
| Topic | **A — Drawing** (`Shape` × `Renderer`) |
| Repository | https://github.com/adilzhandev/design-pattern-bridge |
| Base commit (I1/I2 working version) | `f792bfcf9a09335e83511945646d362ce0a2c29d` |
| Extension commit (I3 added) | `be6c91dbb5c5785a017b3d81de69b58e8569dace` |

## Role map

| Role | Class | Source path |
|---|---|---|
| Abstraction | `Shape` (abstract) | [src/drawing/shape/Shape.java](src/drawing/shape/Shape.java) |
| A1 — refined abstraction | `Circle` | [src/drawing/shape/Circle.java](src/drawing/shape/Circle.java) |
| A2 — refined abstraction | `Square` | [src/drawing/shape/Square.java](src/drawing/shape/Square.java) |
| Implementor | `Renderer` (interface) | [src/drawing/renderer/Renderer.java](src/drawing/renderer/Renderer.java) |
| I1 — concrete implementor | `VectorRenderer` | [src/drawing/renderer/VectorRenderer.java](src/drawing/renderer/VectorRenderer.java) |
| I2 — concrete implementor | `RasterRenderer` | [src/drawing/renderer/RasterRenderer.java](src/drawing/renderer/RasterRenderer.java) |
| I3 — added in extension | `AsciiRenderer` | [src/drawing/renderer/AsciiRenderer.java](src/drawing/renderer/AsciiRenderer.java) |
| Client | `Main` (default package) | [src/Main.java](src/Main.java) |

## Where to look

| What | Location |
|---|---|
| Bridge field `private Renderer renderer` | `Shape.java:10` |
| Renderer supplied through the constructor | `Shape.java:12` |
| `public String execute()` | `Shape.java:17` |
| `public void setImplementation(Renderer renderer)` | `Shape.java:21` |
| Delegation to the implementor (`draw`) | `Circle.java:19`, `Square.java:19` |
| T1–T4, T6, T7 check (`checkCombination`) | `Main.java` — `checkCombination(...)` |
| T5 runtime switch check (`checkRuntimeSwitch`) | `Main.java` — `checkRuntimeSwitch(...)`, `sameObject = shape == original` |

Call path for one check: `Main` → `Shape.execute()` → `Circle.draw(renderer)` → `Renderer.renderCircle(radius)` → `VectorRenderer.renderCircle(2)`.

## Build and run

From the project root (JDK 17 or newer, no extra dependencies):

```bash
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
```

```bash
java -cp out Main --demo
```

## Expected results

Sample data: `Circle` radius **2**, `Square` side **3**.

| Check | Setup | Expected result |
|---|---|---|
| T1 | Circle + VectorRenderer | `VECTOR circle radius=2` |
| T2 | Circle + RasterRenderer | `RASTER circle radius=2 pixels=4x4` |
| T3 | Square + VectorRenderer | `VECTOR square side=3` |
| T4 | Square + RasterRenderer | `RASTER square side=3 pixels=3x3` |
| T5 | One Circle `C-5`: VectorRenderer, then `setImplementation(RasterRenderer)` | `sameObject=true`, `stateUnchanged=true` (id `C-5`, radius 2), before `VECTOR circle radius=2`, after `RASTER circle radius=2 pixels=4x4` |
| T6 | Circle + AsciiRenderer | `ASCII circle radius=2 art=(oooo)` |
| T7 | Square + AsciiRenderer | `ASCII square side=3 art=###/###/###` |

Final line: `SUMMARY: 7/7 PASS`. The captured output is in [demo-output.txt](demo-output.txt).

## Extension

`AsciiRenderer` was added after the base commit. The source diff is in [extension.diff](extension.diff):

```bash
git diff f792bfcf9a09335e83511945646d362ce0a2c29d HEAD -- src > extension.diff
```

Only `src/drawing/renderer/AsciiRenderer.java` (new) and `src/Main.java` (T6, T7) changed inside `src/`.

## Repository contents

| Item | Contents |
|---|---|
| `src/` | Java sources |
| `sources.txt` | Source list for `javac` |
| `README.md` | This file |
| `report.pdf` | Short report with the UML diagram |
| `demo-output.txt` | Output of `java -cp out Main --demo` |
| `extension.diff` | Diff from the base commit to the I3 extension |
| `docs/uml/` | UML class diagram (SVG and PNG) |
