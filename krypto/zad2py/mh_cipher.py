from simple_key_generator import SimpleKeyGenerator


class MHCipher:
    def __init__(self):
        self.keygen = SimpleKeyGenerator()
        self.private_key = None
        self.public_key = None
        self.generate_keys()

    def generate_keys(self):
        self.private_key = self.keygen.generate_default_private_key()
        self.public_key = self.keygen.generate_default_public_key(self.private_key)

    def generate_public_key(self):
        self.public_key = self.keygen.generate_default_public_key(self.public_key)

    def encrypt(self, message):
        message_length = len(message)

        if message_length == 0:
            return

        output = []
        for i in message:
            output.append(self.calculate_encrypted_char(i))
        return output

    def decrypt(self, cipher):
        output = ""
        multi_inverse = self.calculate_multiplier_module_inverse()
        print(multi_inverse)

        for i in cipher:
            encrypted_char = (multi_inverse * i) % self.keygen.modulus
            output += self.decrypt_char(encrypted_char)
        return output

    def decrypt_char(self, encrypted_char):
        msb = 128
        output_char = 0
        for i in range(7, -1, -1):
            if encrypted_char <= 0:
                break
            if encrypted_char >= self.private_key[i]:
                encrypted_char -= self.private_key[i]
                output_char += msb
            msb /= 2
        return chr(int(output_char))

    def calculate_encrypted_char(self, char):
        output = 0
        ascii_value = ord(char)
        for i in range(8):
            if ascii_value % 2 != 0:
                output += self.public_key[i]
            ascii_value /= 2
            ascii_value = int(ascii_value)
        return output

    def extended_euclid(self, c, d):
        if d == 0:
            return [1, 0]

        response = self.extended_euclid(d, c % d)
        a = response[1]
        b = response[0] - (int(c / d) * response[1])
        return [a, b]

    def calculate_multiplier_module_inverse(self):
        # response = self.extended_euclid(self.keygen.multiplier, self.keygen.modulus)
        # return response[0]
        return self.egcd(self.keygen.multiplier, self.keygen.modulus)

    def egcd(self, a, b):
        x, y, u, v = 0, 1, 1, 0
        while a != 0:
            q, r = b // a, b % a
            m, n = x - u * q, y - v * q
            b, a, x, y, u, v = a, r, u, v, m, n
        gcd = b
        return x
