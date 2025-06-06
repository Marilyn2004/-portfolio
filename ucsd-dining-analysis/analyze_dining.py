
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os

# Load data
df = pd.read_csv("final project data.csv")

# Normalize text
for col in df.columns:
    if df[col].dtype == 'object':
        df[col] = df[col].str.strip().str.lower()

# Create output folder
os.makedirs("charts", exist_ok=True)

# Plot 1: Device vs Attention
sns.countplot(data=df, x='devices', hue='attention')
plt.title("Device Type vs Attention Level")
plt.savefig("charts/device_vs_attention.png")
plt.clf()

# Plot 2: Friends vs Talking
sns.countplot(data=df, x='friends', hue='talk')
plt.title("Friends Present vs Talking Behavior")
plt.savefig("charts/friends_vs_talk.png")
plt.clf()

# Plot 3: Food vs Duration
sns.countplot(data=df, x='food', hue='duration')
plt.title("Food Amount vs Meal Duration")
plt.savefig("charts/food_vs_duration.png")
plt.clf()

# Plot 4: Interval vs Duration
sns.countplot(data=df, x='interval', hue='duration')
plt.title("Interval Before Leaving vs Duration")
plt.savefig("charts/interval_vs_duration.png")
plt.clf()
