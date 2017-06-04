from decimal import *
from math import gcd
from random import random
from key_gen_utils import multinv


def signature(msg, privkey):
    f = open('signedfile', 'w')
    coded = pow(int(msg), *privkey) % privkey[1]
    print("Blinded Signed Message:     " + str(coded))
    f.write(str(coded))


def blindingfactor(N):
    b = Decimal(Decimal(random()) * (N - 1))
    r = int(b)
    while gcd(r, N) != 1:
        r = r + 1
    return r


def blind(msg, pubkey):
    f = open('blindmsg', 'w')
    r = blindingfactor(pubkey[1])
    m = int(msg)
    blindmsg = (pow(r, *pubkey) * m) % pubkey[1]
    print("Blinded Message:            " + str(blindmsg))
    f.write(str(blindmsg))
    return r


def unblind(msg, r, pubkey):
    f = open('unblindsigned', 'w')
    bsm = int(msg)
    ubsm = (bsm * multinv(pubkey[1], r)) % pubkey[1]
    print("Unblinded Signed Message:   " + str(ubsm))
    f.write(str(ubsm))


def verefy(msg, pubkey):
    f = open('verified_message', 'w')
    verefy_message = str(pow(int(msg), *pubkey) % pubkey[1])
    print("Message After Verification: " + verefy_message)
    while len(verefy_message) % 3 != 0:
        verefy_message = "0" + verefy_message

    f.write(verefy_message)


def hash_to_int(hash_message):
    int_message = ""
    for i in hash_message:
        number = str(ord(i))
        while len(number) < 3:
            number = "0" + number
        int_message += number
    return int_message


def int_to_hash(int_message):
    str_message = ""
    last = ""
    j = 0
    for i in int_message:
        if j % 3 == 0 or j % 3 == 1:
            last += i
        else:
            str_message += chr(int(last + i))
            last = ""
        j += 1
    return str_message
