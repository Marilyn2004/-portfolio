
from dining_data_processor import DiningDataProcessor
from plot_utils import plot_count

def main():
    processor = DiningDataProcessor("final project data.csv")
    df = processor.clean_data()

    pines_df = processor.get_restaurant_data("pines")
    plot_count(pines_df, "friends", "talk", "Friends Present vs Talking Behavior (Pines)", "friends_vs_talk_pines.png")
    plot_count(pines_df, "interval", "duration", "Interval Before Leaving vs Duration (Pines)", "interval_vs_duration_pines.png")
    plot_count(pines_df, "food", "duration", "Food Amount vs Meal Duration (Pines)", "food_vs_duration_pines.png")
    plot_count(pines_df, "devices", "attention", "Device Usage vs. Attention Level (Pines)", "device_vs_attention_pines.png")

if __name__ == "__main__":
    main()
