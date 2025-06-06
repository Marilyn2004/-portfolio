
package main

import (
    "encoding/json"
    "fmt"
    "os"
)

type Waypoint struct {
    ID        string  `json:"id"`
    Latitude  float64 `json:"latitude"`
    Longitude float64 `json:"longitude"`
    Altitude  int     `json:"altitude"`
    Task      string  `json:"task"`
}

type Mission struct {
    MissionName string     `json:"mission"`
    Waypoints   []Waypoint `json:"waypoints"`
}

func main() {
    file, err := os.ReadFile("sample_map_data.json")
    if err != nil {
        fmt.Println("Error reading file:", err)
        return
    }

    var mission Mission
    err = json.Unmarshal(file, &mission)
    if err != nil {
        fmt.Println("Error parsing JSON:", err)
        return
    }

    fmt.Println("Mission:", mission.MissionName)
    for _, wp := range mission.Waypoints {
        fmt.Printf("→ %s: lat=%.4f, lon=%.4f, alt=%dm, task=%s\n",
            wp.ID, wp.Latitude, wp.Longitude, wp.Altitude, wp.Task)
    }
}
