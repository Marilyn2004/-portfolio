
import pandas as pd

class DiningDataProcessor:
    def __init__(self, filepath):
        self.filepath = filepath
        self.df = pd.read_csv(filepath)

    def clean_data(self):
        self.df.fillna("unknown", inplace=True)
        return self.df

    def get_restaurant_data(self, location_name):
        return self.df[self.df["location"].str.lower() == location_name.lower()]
