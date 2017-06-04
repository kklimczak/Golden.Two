from tkinter import *
from tkinter import ttk

root = Tk()

root.title("Blind Digital Signature By RSA")

main_frame = ttk.Frame(root)
main_frame.grid(column=0, row=0, sticky=(N, W, E, S))
main_frame.columnconfigure(0, weight=1)
main_frame.rowconfigure(0, weight=1)

message = StringVar()
public_key = StringVar()
private_key = StringVar()
message_as_ints = StringVar()
blinded_message = StringVar()
blinded_signed_message = StringVar()
unblinded_signed_message = StringVar()

ttk.Label(main_frame, text="Your message").grid(column=1, row=1, sticky=W)
message_entry = ttk.Entry(main_frame, textvariable=message)
message_entry.grid(column=1, row=2, columnspan=2, sticky=(W, E))

ttk.Separator(main_frame, orient=HORIZONTAL).grid(column=1, row=3, columnspan=2, sticky=(W, E))

ttk.Button(main_frame, text="Generate public and private keys").grid(column=1, row=4, sticky=W)
ttk.Label(main_frame, text="Public key").grid(column=1, row=5, sticky=W)
public_key_entry = ttk.Entry(main_frame, width=50, textvariable=public_key)
public_key_entry.grid(column=1, row=6, sticky=W)

ttk.Label(main_frame, text="Private key").grid(column=2, row=5, sticky=W)
private_key_entry = ttk.Entry(main_frame, width=50, textvariable=private_key)
private_key_entry.grid(column=2, row=6, sticky=W)

ttk.Separator(main_frame, orient=HORIZONTAL).grid(column=1, row=7, columnspan=2, sticky=(W, E))

ttk.Button(main_frame, text="Blind message").grid(column=1, row=8, sticky=W)
ttk.Label(main_frame, text="Blinded message").grid(column=1, row=9, sticky=W)
blinded_message_entry = ttk.Entry(main_frame, textvariable=blinded_message)
blinded_message_entry.grid(column=1, row=10, columnspan=2, sticky=(W, E))

ttk.Separator(main_frame, orient=HORIZONTAL).grid(column=1, row=11, columnspan=2, sticky=(W, E))

ttk.Button(main_frame, text="Sign message").grid(column=1, row=12, sticky=W)
ttk.Label(main_frame, text="Signed message").grid(column=1, row=13, sticky=W)
blinded_signed_message_entry = ttk.Entry(main_frame, textvariable=blinded_signed_message)
blinded_signed_message_entry.grid(column=1, row=14, columnspan=2, sticky=(W, E))

ttk.Separator(main_frame, orient=HORIZONTAL).grid(column=1, row=15, columnspan=2, sticky=(W, E))

ttk.Button(main_frame, text="Unblind message").grid(column=1, row=16, sticky=W)
ttk.Label(main_frame, text="Unblinded message").grid(column=1, row=17, sticky=W)
unblinded_signed_message_entry = ttk.Entry(main_frame, textvariable=unblinded_signed_message)
unblinded_signed_message_entry.grid(column=1, row=18, columnspan=2, sticky=(W, E))

for child in main_frame.winfo_children(): child.grid_configure(padx=5, pady=5)

root.style = ttk.Style()
root.style.theme_use('clam')

root.mainloop()
