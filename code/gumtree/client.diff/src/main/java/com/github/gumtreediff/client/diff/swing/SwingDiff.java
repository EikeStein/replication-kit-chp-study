/*
 * This file is part of GumTree.
 *
 * GumTree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GumTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GumTree.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2019 Jean-Rémy Falleri <jr.falleri@gmail.com>
 */

package com.github.gumtreediff.client.diff.swing;

import com.github.gumtreediff.actions.ChawatheScriptGenerator;
import com.github.gumtreediff.actions.Diff;
import com.github.gumtreediff.client.Register;
import com.github.gumtreediff.client.diff.AbstractDiffClient;
import com.github.gumtreediff.client.diff.swing.MappingsPanel;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;

import javax.swing.*;

@Register(description = "A swing diff client", options = AbstractDiffClient.Options.class)
public final class SwingDiff extends AbstractDiffClient<AbstractDiffClient.Options> {

    public SwingDiff(String[] args) {
        super(args);
    }

    @Override
    public void run() {
        final MappingStore mappings = matchTrees();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("GumTree");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Diff diff = new Diff(getSrcTreeContext(), getDstTreeContext(), mappings,
                        new ChawatheScriptGenerator().computeActions(mappings));
                frame.add(new MappingsPanel(opts.src, opts.dst, diff));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    @Override
    protected AbstractDiffClient.Options newOptions() {
        return new AbstractDiffClient.Options();
    }
}