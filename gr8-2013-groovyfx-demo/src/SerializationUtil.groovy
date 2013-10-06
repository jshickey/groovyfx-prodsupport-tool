class SerializationUtil
{
	void serializeObjectToFile(Object o, String filename) {
        new ObjectOutputStream(
			new FileOutputStream(filename)).writeObject(o);
    }

	def deserializeObjectFromFile(String filename) {
		new ObjectInputStream(
			new FileInputStream(filename)).readObject()
	}
}

