# Aplikace smarthome


Aplikace Smarthome aplikace je simulace chování inteligentního domu.

## Členové týmu
- Jan Košler (kosleja1)
- Kryštof Ježek (jezekkr2)

## Popis aplikace

Aplikace načítá konfiguraci inteligentního domu ze souboru ve formátu JSON a 
poté spustí přednastavenou simulaci chodu domu.
Základní objekt je dům, který obsahuje patra.
Každá domácnost obsahuje garáž, která je vždy na nejnižším patře.
Každé patro obsahuje místnosti, které obsahují zařízení, lidi a zvířata.
Zařízení jsou reprezentována systémy, které obsahují zařízení. 
Uživetelé domu komunikují s těmito systémy za pomocí příkazů.
Pro více informací o konfiguraci viz níže. 

## Nastavení simulace
### Seznam podporovaných systémů zařízení (s zařízeními které obsahují):
    1) (FridgeSystem) Systém pro lednici :
            - (Fridge) - Lednice 
            - (FridgeController) - Ovladač lednice 
            - (UserInputSensor) - Senzor uživatelského vstupu 

    2) (GateControlSystem) Systém pro garážová vrata :
            - (Gate) - Vrata 
            - (GateController) - Ovladač vrat
            - (UserInputSensor) - Senzor uživatelského vstupu 

    3) (HVACSystem) Systém pro vzduchotechniku :
            - (HVAC) - Vzduchotechnika
            - (TemperatureController) - Ovladač teploty
            - (TemperatureController) - Vnější senzor teploty
            - (TemperatureController) - Vnitřní senzor teploty
            - (UserInputSensor) - Senzor uživatelského vstupu

    4) (LightningSystem) Systém pro osvětlení :
            - (Light) - světla ovládané tímto systémem (List<Light>)
            - (LightController) - Ovladač světel
            - (MotionSensor) - Pohybové čidlo
            - (UserInputSensor) - Senzor uživatelského vstupu

    5) (SecuritySystem) Systém pro zabezpečení :
            - (Alarm) - alarm
            - (SecuritySensor) - senzor alarmu
            - (SecurityController) - ovladač alarmu
            - (UserInputSensor) - Senzor uživatelského vstupu

    6) (TVSystem) Systém pro televizi :
            - (TV) - Televize
            - (TVController) - Ovladač televize
            - (UserInputSensor) - Senzor uživatelského vstupu

## Běh simulace a reporting
Domácnost má členy domácnosti (lidé a zvířata), kteří mohou ovládat systémy zařízení.
Osoby a zažízení dále vytváří eventy, které jsou zpracovávány systémy zařízení. 
Zařízení vytváří například eventy nutnosti údržby, které jsou potom zpracovávány lidmi.
Při údržbě si osoba, která provádí opravu, vyžádá manuál k opravě, 
pokud takový manuál existuje, provede se úplná oprava, 
jinak se zařízení opraví jen z části, tak aby bylo funkční, ale ne zcela opravené.

Chod simulace je možné monitorovat pomocí reportů. Možné reporty jsou

    1) (ActivityAndUsageReport)
        - Reportuje aktivitu členů domácnosti a použití zařízení v domě.
    2) (ConsumptionReport)
        - Reportuje spotřebu energie zařízení v domě.
    3) (EventReport)
        - Reportuje všechny eventy vyvolané ovladači v domě.
    4) (HouseConfigurationReport)
        - Reportuje konfiguraci domu.

Reporty se ukládají do složky "reports" v kořenovém adresáři aplikace.

## Upravení průběhu simulace
Průběh simulace je možné upravit v metodě *simulate()* v souboru *HouseFacade.java*. 
Starajícího se o nastavení průběhu simulace.

## Vytvoření konfiguračního souboru
Soubor **.json** je načítán ze složky "config" v kořenovém adresáři aplikace. Cesta je vytvářena z pracovního adresáře aplikace.
**Příklad cesty: "OMO-semestral-work\config\config.json"**
### JSON config formát pro simulaci
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

"Floors": // array of floors
[
    {
        "floorID": int, // unique
        "floorName": string, // unique
        "floorLevel": int // unique
    },
], 
"Garage":
{   // garage always occupies lowest floor
    "roomID": int, // must be zero, zero is reserved for garage
    "garageName": string,
    "garageHouseID": int,
    "sportEquipmentCountBIKE": int, // more than 0
    "sportEquipmentCountSKATES": int, // more than 0
    "sportEquipmentCountSKIS": int // more than 0
},
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
        "systemName": string, // used for system type, must be one of the supported types
        "roomID": int // must exist in "Rooms"
    },
]
}
```

## Použité design patterny:
1) Facade - HouseFacade.java, agreguje načítání konfigurace, spuštění simulace a reporting.
2) Observer - MalfunctionObserver (Sleduje zprávy o poruše zařízení a zpracovává je), IDeviceObserver (Controller sleduje změny na Sensorech)
3) Factory - DeviceSystemactory (Vytváří systémy zařízení)
4) StateMachine - HVACState (Různé stavy zařízení HVAC - Vypnutý, Chladí, Ohřívá, Ventiluje)
5) Visitor - ReportVisitor (vytváření reportů – přijde, získá informace, odejde – nic nezmění v rámci objektů)
6) Template Method - Abstraktní třídy Sensor, Appliance a Controller (Třídy mají metody, které jsou přepsány v potomcích)
7) Command - UserInputSensorCommand (Příkaz pro UserInputSensor), DeviceCommand (Příkazy, které Controllery posílají na Appliances)
8) Singleton - Např. TickPublisher (Vytváří se jen jedna instance)
9) Builder - GarageBuilder (Vytváří garáž)