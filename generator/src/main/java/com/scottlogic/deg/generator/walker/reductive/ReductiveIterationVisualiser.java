package com.scottlogic.deg.generator.walker.reductive;

import com.scottlogic.deg.generator.ProfileFields;
import com.scottlogic.deg.generator.decisiontree.ConstraintNode;
import com.scottlogic.deg.generator.decisiontree.DecisionTree;
import com.scottlogic.deg.generator.decisiontree.visualisation.DecisionTreeVisualisationWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.concurrent.atomic.AtomicInteger;

public class ReductiveIterationVisualiser implements IterationVisualiser {
    private final AtomicInteger currentIteration = new AtomicInteger();
    private final Path visualiseDirectoryPath;

    public ReductiveIterationVisualiser(Path outputDirectory) {
        this.visualiseDirectoryPath = outputDirectory.resolve("reductive-walker");
    }

    @Override
    public void visualise(ConstraintNode rootNode, ReductiveState reductiveState) throws IOException {
        createVisualiseDirectoryIfAbsent();

        int iteration = currentIteration.getAndIncrement();

        ProfileFields profileFields = reductiveState.getFields();
        String description = String.format("Iteration %d\n%s", iteration, reductiveState.toString(true));
        Path outputPath = visualiseDirectoryPath.resolve(String.format("Reduced-tree-%03d.gv", iteration));

        //copy of Visualise.writeTreeTo()
        try (OutputStreamWriter outWriter = new OutputStreamWriter(
            new FileOutputStream(outputPath.toString()),
            StandardCharsets.UTF_8)) {

            new DecisionTreeVisualisationWriter(outWriter).writeDot(
                new DecisionTree(rootNode, profileFields, description),
                "tree",
                description);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createVisualiseDirectoryIfAbsent() throws IOException {
        File directory = this.visualiseDirectoryPath.toFile();
        if (directory.exists()){
            if (directory.isDirectory()){
                return; //exists
            }

            throw new IOException("Cannot visualise iterations of the tree, a file exists at the path where the" +
                " directory needs to exist.\n" + this.visualiseDirectoryPath.toString());
        }

        FileSystemProvider provider = FileSystems.getDefault().provider();
        provider.createDirectory(this.visualiseDirectoryPath);
    }
}