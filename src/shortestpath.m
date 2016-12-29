

function shortestPath = shortestpath (adjMatrix, vertex1,vertex2)
  edge1 = getFirstEdge(vertex1,adjMatrix);
  nodeList = getFirstNodeList(edge1,vertex1);
  
  edges = edge1;
  for(i=1:rows(nodeList))
    if(i ~= vertex1)
      if(find(nodeList(i))) 
        edges = vertcat(edges,nodeList(i,:));
      endif;
    endif;
  endfor;
  
  vertex2Found = containsVertex(edge1,vertex2);
  
  while(~vertex2Found)
    nextEdge = determineNextEdge(edges,nodeList,adjMatrix);
    nodeList(nextEdge(2),:) = nextEdge(:);
    edges = vertcat(edges,nextEdge);
    vertex2Found = containsVertex(nextEdge,vertex2);
  endwhile;
  
  noLooseEnds = false;
  previousNode = vertex2;
  shortestPath = [];
  while(~noLooseEnds)
    v = nodeList(previousNode,1:2);
    shortestPath = vertcat(v,shortestPath);
    previousNode = v(1);
    if(v(1)==v(2)) noLooseEnds = true;
    endif;
  endwhile;
  
  

endfunction
