from task_manager import TaskManager

def display_tasks(tasks):
    print("\nYour Tasks:")
    for i, task in enumerate(tasks):
        status = "✅" if task["completed"] else "❌"
        print(f"{i}. [{status}] {task['description']} (Priority: {task['priority']})")
    print()

def main():
    manager = TaskManager()

    while True:
        print("\n==== ToDo List Menu ====")
        print("1. Add Task")
        print("2. Complete Task")
        print("3. Delete Task")
        print("4. View Tasks")
        print("5. View Progress")
        print("6. Exit")

        choice = input("Select an option: ")

        if choice == "1":
            desc = input("Enter task description: ")
            priority = input("Priority (High/Medium/Low): ").capitalize()
            manager.add_task(desc, priority)

        elif choice == "2":
            display_tasks(manager.get_sorted_tasks())
            idx = int(input("Enter task index to complete: "))
            manager.complete_task(idx)

        elif choice == "3":
            display_tasks(manager.get_sorted_tasks())
            idx = int(input("Enter task index to delete: "))
            manager.delete_task(idx)

        elif choice == "4":
            display_tasks(manager.get_sorted_tasks())

        elif choice == "5":
            progress = manager.show_progress()
            print(f"✅ Completed: {progress}%")

        elif choice == "6":
            print("Goodbye!")
            break
        else:
            print("Invalid option.")

if __name__ == "__main__":
    main()
