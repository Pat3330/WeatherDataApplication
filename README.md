# Weather Data Application

This is an pplication that loads weather data from a CSV file and provides
features like checking the average temperature for a month, rainy days, and days
above a threshold that the user is able to chnage. 

---

## Features

- **CSV Weather Data Loading**
  - Loads `weatherData.csv` from the application resources.
  - Parses each line into a `WeatherRecord` using a stream pipeline.

- **Interaction Elements**
  - Average Temperature for a selected month.
  - Get rainy days by counting days with precipitation greater than 0.
  - Get days above a threshold based on a user‑entered temperature.
  - Recieve a category and description for a given temperature.

- **JavaFX UI**
  - Text fields for input (temperature, threshold, month).
  - Buttons to trigger analytics.
  - A result area that displays formatted summaries.

---
