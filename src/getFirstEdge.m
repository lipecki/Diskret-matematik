function edge = getFirstEdge (node, weightedAdjacencyMatrix)
  [j,k,l] = find(weightedAdjacencyMatrix(node,:));
  edges=[];
  for  i=1:length(j)
    edge = horzcat(node,k(i),l(i));
    edges = vertcat(edges,edge);
  endfor;
  edges = sortrows(edges,3);
  edge = edges(1,:);
endfunction
