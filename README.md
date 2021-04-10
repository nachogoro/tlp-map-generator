**Disclaimer:** This project has been developed to aid with the verification of a homework project, as part of "Teoría de los Lenguajes de Programación" in UNED for the year 2020-2021. Knowledge of such homework project is assumed, and the usefulness of this project is non-existent outside of the subject.

# Map generator for TLP
This projects makes it easier to verify the correctness of the solution to the four-color problem coded as part of TLP.

It has two main classes:
* `MainSingleMap`: it generates a single map with the specified characteristics. That map can be saves into a `.map` file and be fed to the main function of the Haskell project.
* `MainBattery`: it generates a battery of tests which is compatible with the battery test verification tool developed by the professors. The generated battery of tests can be fed to the verification tool by storing it in a file named `TestTLP2021Estudiantes.map` in the directory of the verification tool.
