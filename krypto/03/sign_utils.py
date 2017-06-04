from decimal import *
from math import gcd
from random import random
from key_gen_utils import multinv


def blinding_factor(n):
    b = Decimal(Decimal(random()) * (n - 1))
    r = int(b)
    while gcd(r, n) != 1:
        r = r + 1
    return r


def blind(message, public_key):
    f = open('blindmsg', 'w')
    r = blinding_factor(public_key[1])
    m = int(message)
    blind_message = (pow(r, *public_key) * m) % public_key[1]
    print("Blinded Message:            " + str(blind_message))
    f.write(str(blind_message))
    return r


def signature(message, private_key):
    f = open('signedfile', 'w')
    coded = pow(int(message), *private_key) % private_key[1]
    print("Blinded Signed Message:     " + str(coded))
    f.write(str(coded))


def unblind(message, r, public_key):
    f = open('unblindsigned', 'w')
    blind_signed_message = int(message)
    unblind_signed_message = (blind_signed_message * multinv(public_key[1], r)) % public_key[1]
    print("Unblinded Signed Message:   " + str(unblind_signed_message))
    f.write(str(unblind_signed_message))


def verify(message, public_key):
    f = open('verified_message', 'w')
    verify_message = str(pow(int(message), *public_key) % public_key[1])
    print("Message After Verification: " + verify_message)
    while len(verify_message) % 3 != 0:
        verify_message = "0" + verify_message

    f.write(verify_message)


def hash_to_int(hash_message):
    int_message = ""
    for i in hash_message:
        number = str(ord(i))
        while len(number) < 3:
            number = "0" + number
        int_message += number
    return int_message
