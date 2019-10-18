import React, { Component } from 'react';
import { StyleSheet, SafeAreaView, Text } from 'react-native';

export default class Main extends Component {
  render() {
    return(
        <SafeAreaView>
            <Text style={styles.textTeste}>Nice carai</Text>
        </SafeAreaView>
    );
  }
}

export const styles = StyleSheet.create({
    container: {
        backgroundColor: '#fff',
    },
    textTeste: {
        color: '#red'
    }
});
