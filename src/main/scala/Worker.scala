object Worker:

  enum Command:
    case UpdateVelocities
    case UpdatePositions
    case Stop
  export Command.*