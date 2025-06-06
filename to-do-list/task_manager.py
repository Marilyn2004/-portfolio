import json
from operator import itemgetter

class TaskManager:
    def __init__(self, filename="tasks.json"):
        self.filename = filename
        self.tasks = []
        self.load_tasks()

    def add_task(self, description, priority="Medium"):
        task = {
            "description": description,
            "priority": priority,
            "completed": False
        }
        self.tasks.append(task)
        self.save_tasks()

    def complete_task(self, index):
        if 0 <= index < len(self.tasks):
            self.tasks[index]["completed"] = True
            self.save_tasks()

    def delete_task(self, index):
        if 0 <= index < len(self.tasks):
            del self.tasks[index]
            self.save_tasks()

    def get_sorted_tasks(self):
        priority_order = {"High": 0, "Medium": 1, "Low": 2}
        return sorted(
            self.tasks,
            key=lambda x: (x["completed"], priority_order.get(x["priority"], 1))
        )

    def show_progress(self):
        if not self.tasks:
            return 0
        completed = sum(1 for t in self.tasks if t["completed"])
        return round(completed / len(self.tasks) * 100, 2)

    def save_tasks(self):
        with open(self.filename, "w") as f:
            json.dump(self.tasks, f, indent=2)

    def load_tasks(self):
        try:
            with open(self.filename, "r") as f:
                self.tasks = json.load(f)
        except (FileNotFoundError, json.JSONDecodeError):
            self.tasks = []
