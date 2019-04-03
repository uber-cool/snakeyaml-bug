package com.snake.yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.reader.UnicodeReader;
import org.yaml.snakeyaml.resolver.Resolver;

public class YamlBug {

  public static void main(String[] args) throws IOException {
    processNodes();
  }

  private static void processNodes() throws IOException {
    Path yamlPath = Paths.get( "nodes.yaml");
    InputStream yamlStream = Files.newInputStream(yamlPath);
    StreamReader sreader = new StreamReader(new UnicodeReader(yamlStream));
    Composer composer = new Composer(new ParserImpl(sreader), new Resolver());
    Node rootNode = composer.getSingleNode();

    SequenceNode nodeList = (SequenceNode) rootNode;
    MappingNode node_1 = (MappingNode) nodeList.getValue().get(0);
    MappingNode node_2 = (MappingNode) nodeList.getValue().get(1);

    MappingNode node_1_prop_node = (MappingNode) node_1.getValue().get(0).getValueNode();
    MappingNode node_2_prop_node = (MappingNode) node_2.getValue().get(0).getValueNode();

    MappingNode node_1_props = (MappingNode) node_1_prop_node.getValue().get(0).getValueNode();
    MappingNode node_2_props = (MappingNode) node_2_prop_node.getValue().get(0).getValueNode();

    List<String> prop1 = node_1_props.getValue().stream().map(prop -> ((ScalarNode) prop.getKeyNode()).getValue()).collect(
        Collectors.toList());
    System.out.println("Node_1 properties: " + prop1);
    List<String> prop2 = node_2_props.getValue().stream().map(prop -> ((ScalarNode) prop.getKeyNode()).getValue()).collect(
        Collectors.toList());
    System.out.println("Node_2 properties: " + prop2);
  }

}
