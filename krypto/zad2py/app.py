from mh_cipher import MHCipher
from tkinter import *
from tkinter import ttk

a = MHCipher()


def encrypt(*args):
    try:
        encoded_value.set(a.encrypt(message_entry.get()))
    except ValueError:
        pass


def decrypt(*args):
    try:
        decoded_value.set(a.decrypt(eval(encoded_value.get())))
    except ValueError:
        pass


root = Tk()

root.title("Knapsack problem")

main_frame = ttk.Frame(root)
main_frame.grid(column=0, row=0, sticky=(N, W, E, S))
main_frame.columnconfigure(0, weight=1)
main_frame.rowconfigure(0, weight=1)

message_var = StringVar()
encoded_value = StringVar()
decoded_value = StringVar()

ttk.Label(main_frame, text="Your message:").grid(column=1, row=1, sticky=W)

message_entry = ttk.Entry(main_frame, width=39, textvariable=message_var)
message_entry.grid(column=2, row=1, sticky=(W, E))
message_entry.focus()

ttk.Button(main_frame, text="Encode", command=encrypt).grid(column=2, row=2, sticky=W)

ttk.Label(main_frame, textvariable=encoded_value, wraplength=450, justify="center").grid(column=1, row=3, columnspan=2,
                                                                                         sticky=W)

ttk.Button(main_frame, text="Decode", command=decrypt).grid(column=2, row=4, sticky=W)

ttk.Label(main_frame, textvariable=decoded_value, wraplength=450, justify="center").grid(column=1, row=5, columnspan=2,
                                                                                         sticky=W)

for child in main_frame.winfo_children(): child.grid_configure(padx=5, pady=5)

root.bind('<Return>', encrypt)
root.bind('<Return>', decrypt)

root.style = ttk.Style()
print(root.style.theme_names())
root.style.theme_use('clam')

root.mainloop()
