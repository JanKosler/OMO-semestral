
### JSON config format example for house simulation
```javascript
{
"House": 
    {
        "houseID": int,
        "houseNumber": int,
        "address": string,
        "internalTemperature": int,
        "externalTemperature": int
    },
"Garage":
    {
        "roomID": int, // must be zero, zero is reserved for garage
        "garageName": string, 
        "garageHouseID": int,
        "sportEquipmentCountBIKE": int, // more than 0
        "sportEquipmentCountSKATES": int, // more than 0
        "sportEquipmentCountSKIS": int // more than 0
    },
"Floors": // array of floors
[
    {
        "floorID": int, // unique
        "floorName": string, // unique
        "floorLevel": int // unique
    },
],

"Rooms": // array of rooms
[
    {
        "roomID": int, // unique, not zero
        "roomName": string, // unique
        "floorID": int // floor must exist in "Floors"
    },
],
"Pets": // array of pets
[
    {
        "petID": int, // unique
        "petName": string, // unique
        "roomID": int // must exist in "Rooms"
    },
],
"Humans": // array of humans
[
    {
        "personID": int, // unique
        "personName": string, // unique
        "roomID": int // must exist in "Rooms"
    },
],
"DeviceSystems": // array of device systems
[ 
    {
        "systemID": int, // unique
        "systemName": string,
        "roomID": int // must exist in "Rooms"
    },
]
}
```