
function nextEdge = determineNextEdge (edges, nodeList, WeightedAdjancencyMatrix)
  possibleEdges = getPossibleEdges(edges,WeightedAdjancencyMatrix);
  nextEdge = findLowestSum(possibleEdges,nodeList);
endfunction
