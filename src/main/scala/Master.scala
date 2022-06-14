object Master:

  enum Command:
    case Start
    case Stop
    case velocitiesUpdated
    case positionsUpdated
  export Command.*



