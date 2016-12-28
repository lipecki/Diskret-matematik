function tree = minimalSpanningTree (matrix)
  edges = getEdges(matrix);
  tree = edges(1,:);
  addedEdges = 1;
  while(addedEdges++ < (rows(matrix)-1))
    possibleEdges = getPossibleEdges(tree,matrix);
    tree = vertcat(tree,possibleEdges(1,:));
  endwhile;
  
  tree = tree(:,1:2);
endfunction
