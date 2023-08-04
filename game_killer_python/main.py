from tkinter import messagebox

import psutil
import time
import subprocess
import tkinter as tk

def find_and_terminate_processes(process_name):
    processes_terminated = 0
    for process in psutil.process_iter(['pid', 'name']):
        if process.info['name'] == process_name:
            psutil.Process(process.info['pid']).terminate()
            processes_terminated += 1
    return processes_terminated > 0

def main():
    processes = ["AssassinsCreedIIGame.exe", "UbisoftGameLauncher.exe", "upc.exe", "UplayWebCore.exe", "steam.exe", "steamwebhelper.exe"]

    countdown_time = 30 * 60

    try:
        subprocess.Popen(["C:\Program Files (x86)\Steam\steam.exe"])
        time.sleep(15)
        subprocess.Popen(["D:\SteamLibrary\steamapps\common\Assassin's Creed 2\\AssassinsCreedIIGame.exe"])

        while countdown_time > 0:
            time.sleep(1)
            countdown_time -= 1

        for i in processes:
            print(i)
            find_and_terminate_processes(i)

        root = tk.Tk()
        root.withdraw()
        messagebox.showinfo('Сообщение', 'Время работы игры истекло')

    except Exception as e:
        print(e)

if __name__ == "__main__":
    main()


