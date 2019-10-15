/**
 * Chuck Norris Jokes App
 * https://github.com/Anyee-Tecnologia/react-native-intern-challenge.git
 */

import React from 'react';
import {StyleSheet, View, Text} from 'react-native';

const App = () => {
  return (
    <>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>Chuck Jokes App</Text>
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionDescription}>
          Essa é a base do projeto. Fique a vontade para fazer qualquer mudança.
        </Text>
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  header: {
    marginVertical: 20,
    alignSelf: 'center',
  },
  section: {
    flex: 1,
    paddingHorizontal: 20,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
  },
  headerTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    fontSize: 18,
    textAlign: 'center',
    fontWeight: '400',
    color: '#303136',
  },
});

export default App;
