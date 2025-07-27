{
  description = "Dev env with JVM 21";

  inputs = {
    # Manage different systems
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem(system:
      let
        pkgs = import nixpkgs {
          inherit system;
        };
      in
      with pkgs;
      {
        devShells.default = mkShellNoCC {
          buildInputs = [ jdk21 ];
        };
      }
    );
}
