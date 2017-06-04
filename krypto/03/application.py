from tkinter import *
from tkinter import ttk
from sign_utils import hash_to_int, blind, signature, unblind, verefy
from key_gen_utils import keygen

pub_key, priv_key = keygen(2 ** 1024)


def blind_message(*args):
    try:
        msg = hash_to_int(message.get())
        msg.rstrip()
        h = open("hashedmsg", "w")
        h.write(msg)
        r = blind(msg, pub_key)
        f = open('blindfactor', 'w')
        f.write(str(r))
        bf = open('blindmsg')
        blinded_message.set(bf.read())
    except ValueError:
        pass


def sign_message(*args):
    try:
        signature(blinded_message.get(), priv_key)
        h = open('signedfile')
        blinded_signed_message.set(h.read())
    except ValueError:
        pass


def unblind_message(*args):
    try:
        f = open("blindfactor")
        unblind(blinded_signed_message.get(), int(f.read()), pub_key)
        i = open("unblindsigned")
        unblinded_signed_message.set(i.read())
    except ValueError:
        pass


def verified_message(*args):
    try:
        verefy(unblinded_signed_message.get(), pub_key)
        f = open("verified_message")
        h = open("hashedmsg")
        v_msg = f.read()
        h_msg = h.read()
        verification_status.set(str(v_msg == h_msg))
    except ValueError:
        pass


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
verification_status = StringVar()

public_key.set(pub_key[0])
private_key.set(priv_key[0])

ttk.Label(main_frame, text="Your message").grid(column=1, row=1, sticky=W)
message_entry = ttk.Entry(main_frame, width=100, textvariable=message)
message_entry.grid(column=1, row=2, columnspan=2, sticky=(W, E))

ttk.Separator(main_frame, orient=HORIZONTAL).grid(column=1, row=3, columnspan=2, sticky=(W, E))

ttk.Label(main_frame, text="Public key").grid(column=1, row=5, sticky=W)
public_key_entry = ttk.Entry(main_frame, width=50, textvariable=public_key)
public_key_entry.grid(column=1, row=6, sticky=W)

ttk.Label(main_frame, text="Private key").grid(column=2, row=5, sticky=W)
private_key_entry = ttk.Entry(main_frame, width=50, textvariable=private_key)
private_key_entry.grid(column=2, row=6, sticky=W)

ttk.Separator(main_frame, orient=HORIZONTAL).grid(column=1, row=7, columnspan=2, sticky=(W, E))

ttk.Button(main_frame, text="Blind message", command=blind_message).grid(column=1, row=8, sticky=W)
ttk.Label(main_frame, text="Blinded message").grid(column=1, row=9, sticky=W)
blinded_message_entry = ttk.Entry(main_frame, textvariable=blinded_message)
blinded_message_entry.grid(column=1, row=10, columnspan=2, sticky=(W, E))

ttk.Separator(main_frame, orient=HORIZONTAL).grid(column=1, row=11, columnspan=2, sticky=(W, E))

ttk.Button(main_frame, text="Sign message", command=sign_message).grid(column=1, row=12, sticky=W)
ttk.Label(main_frame, text="Signed message").grid(column=1, row=13, sticky=W)
blinded_signed_message_entry = ttk.Entry(main_frame, textvariable=blinded_signed_message)
blinded_signed_message_entry.grid(column=1, row=14, columnspan=2, sticky=(W, E))

ttk.Separator(main_frame, orient=HORIZONTAL).grid(column=1, row=15, columnspan=2, sticky=(W, E))

ttk.Button(main_frame, text="Unblind message", command=unblind_message).grid(column=1, row=16, sticky=W)
ttk.Label(main_frame, text="Unblinded message").grid(column=1, row=17, sticky=W)
unblinded_signed_message_entry = ttk.Entry(main_frame, textvariable=unblinded_signed_message)
unblinded_signed_message_entry.grid(column=1, row=18, columnspan=2, sticky=(W, E))

ttk.Button(main_frame, text="Verify", command=verified_message).grid(column=1, row=19, sticky=W)
ttk.Label(main_frame, text="Verification status").grid(column=1, row=20, sticky=W)
verification_status_entry = ttk.Entry(main_frame, textvariable=verification_status)
verification_status_entry.grid(column=1, row=21, columnspan=2, sticky=(W, E))

for child in main_frame.winfo_children(): child.grid_configure(padx=5, pady=5)

root.bind('<Return>', blind_message)
root.bind('<Return>', sign_message)
root.bind('<Return>', unblind_message)
root.bind('<Return>', verified_message)

root.style = ttk.Style()
root.style.theme_use('clam')

root.mainloop()
