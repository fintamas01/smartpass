#include <Arduino.h>

void setup()
{
    Serial.begin(115200);

    delay(1000);

    Serial.println();
    Serial.println("==============================");
    Serial.println("SmartPass Reader");
    Serial.println("ESP32 boot successful");
    Serial.println("==============================");
}

void loop()
{
    Serial.println("ESP32 is alive.");

    delay(2000);
}