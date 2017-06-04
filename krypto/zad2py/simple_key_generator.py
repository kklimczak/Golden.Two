class SimpleKeyGenerator:
    def __init__(self):
        self.multiplier = 4571
        self.modulus = 18446744073709551615
        self.key_size = 8

    def generate_default_private_key(self):
        new_private_key = []
        key_element = 18446744073709551615
        for i in range(self.key_size):
            key_element /= 2
            new_private_key.append(int(key_element))
        return list(reversed(new_private_key))

    def generate_default_public_key(self, private_key):
        new_public_key = []
        index = 0
        for i in private_key:
            new_public_key.append((i * self.multiplier) % self.modulus)
            index += 1
        return new_public_key
