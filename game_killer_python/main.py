from tkinter import messagebox

import psutil
import time
import subprocess
import tkinter as tk

def find_and_terminate_processes(process_name):
    """
    Ищет и завершает все процессы с заданным именем.

    :param process_name: Имя процесса для завершения.
    :type process_name: str
    """
    for process in psutil.process_iter(['pid', 'name']):
        if process.info['name'] == process_name:
            psutil.Process(process.info['pid']).terminate()


def main():
    """
    Основная функция программы для запуска игры и контроля её времени работы.
    """
    # Список процессов, которые будут завершены после окончания работы игры
    processes = ["AssassinsCreedIIGame.exe", "UbisoftGameLauncher.exe", "upc.exe", "UplayWebCore.exe", "steam.exe", "steamwebhelper.exe"]

    # Время, в течение которого игра может работать (40 минут)
    countdown_time = 40 * 60

    try:
        # Запуск Steam
        subprocess.Popen(["C:\Program Files (x86)\Steam\steam.exe"])
        time.sleep(15)
        # Запуск Assassin's Creed II
        subprocess.Popen(["D:\SteamLibrary\steamapps\common\Assassin's Creed 2\AssassinsCreedIIGame.exe"])
        time.sleep(15)

        # Отсчет времени работы игры
        while countdown_time > 0:
            time.sleep(1)
            countdown_time -= 1

        # Завершение процессов игры и связанных с ней приложений
        for i in processes:
            find_and_terminate_processes(i)

        # Вывод сообщения о завершении работы игры
        root = tk.Tk()
        root.withdraw()
        messagebox.showinfo('Сообщение', 'Время работы игры истекло')

    except Exception as e:
        print(e)

if __name__ == "__main__":
    main()


