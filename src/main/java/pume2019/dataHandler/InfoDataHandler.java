package pume2019.dataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import pume2019.domain.Info;
import pume2019.domain.Tree;

public class InfoDataHandler {

    public InfoDataHandler() {
    }

    public List<Tree> getInfoData(Info info) throws IOException {
        List<Tree> trees = new ArrayList<>();
        trees = parse(info);
        return trees;
    }

    public List<Tree> parse(Info info) throws IOException {
        List<String> speciesIds = new ArrayList<>();
        String[] initFile = info.getInitPath().split("/");
        String file = initFile[initFile.length - 1];
        RFileHandler fileHandler = new RFileHandler(file);
        speciesIds = fileHandler.read();
        String[] ids = speciesIds.get(1).split(",");
        List<Tree> trees = new ArrayList<>();
        List<Integer> counters = new ArrayList<>();
        int pineCounter = 0;
        int spruceCounter = 0;
        int birchCounter = 0;
        counters.add(pineCounter);
        counters.add(spruceCounter);
        counters.add(birchCounter);
        for (int i = 1; i < ids.length; i++) {
            int id;
            String regex = "[0-3]+";
            if (ids[i].matches(regex)) {
                id = Integer.parseInt(ids[i]);

            } else {
                id = 0;
            }
            trees.add(getTree(id, counters));
        }
        trees = renameTrees(trees, counters);
        return trees;
    }

    public Tree getTree(int id, List<Integer> counters) {
        Tree tree = null;
        switch (id) {
            case 1:
                tree = new Tree("Pine", id);
                counters.set(0, counters.get(0) + 1);
                break;
            case 2:
                tree = new Tree("Spruce", id);
                counters.set(1, counters.get(1) + 1);
                break;
            case 3:
                tree = new Tree("Birch", id);
                counters.set(2, counters.get(2) + 1);
                break;
            default:
                tree = new Tree("Empty", id);
                break;
        }
        return tree;
    }

    public List<Tree> renameTrees(List<Tree> trees, List<Integer> counters) {
        for (int i = 0; i < counters.size(); i++) {
            if (i == 0 && counters.get(i) > 1) {
                rename(trees, 1, counters.get(i));
            } else if (i == 1 && counters.get(i) > 1) {
                rename(trees, 2, counters.get(i));
            } else if (i == 2 && counters.get(i) > 1) {
                rename(trees, 3, counters.get(i));
            }
        }

        return trees;
    }

    public List<Tree> rename(List<Tree> trees, int id, int counterVal) {
        for (int i = trees.size() - 1; i > -1; i--) {
            Tree tree = trees.get(i);
            if (tree.getId() == id) {
                tree.setName(tree.getName() + " " + (counterVal));
                counterVal--;
            }
        }
        return trees;
    }
}
