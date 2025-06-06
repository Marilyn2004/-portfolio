
import matplotlib.pyplot as plt
import seaborn as sns

def plot_count(data, x, hue, title, filename):
    plt.figure(figsize=(6, 4))
    sns.countplot(data=data, x=x, hue=hue)
    plt.title(title)
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()
