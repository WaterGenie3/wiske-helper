# Wiske Helper

Ad-hoc mod to help test wither skeleton spawning.

This tool is now superceded by [`spawn-forcer`](https://github.com/WaterGenie3/spawn-forcer).

- y level samples uniformly from 50 to heightmap + 1 instead of world bottom to heightmap + 1.
  - If heightmap < 50, it always pick heightmap + 1
  - This is just to speed up the spawning in hope of speeding up testing for small differences.
- Pack spawning jumps in x and z follows a uniform distribution between -5 and 5 instead of a triangular distribution between -5 and 5.
  - This is so that any affects from larger skirts are easier to see.
