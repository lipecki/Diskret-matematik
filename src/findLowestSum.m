

function edgeWithLowestTotal = findLowestSum (possibleEdges, nodeList)
  edgeWithLowestTotal = possibleEdges(1,:);
  edgeSums = zeros(3);
  countEdgeSums = 1;
  [j,k,l] = find(nodeList);
  for(i=1:length(j))
    for(m=1:rows(possibleEdges))
      b = j(i);
      x = possibleEdges(m,1);
      y = possibleEdges(m,2);
      summ = 0;
      if(b == x)
        summ = (nodeList(b,3)+possibleEdges(m,3));
      elseif(b == y)
        summ = (nodeList(b,3)+possibleEdges(m,3));
      endif;
      edgeSums(countEdgeSums++,:) = horzcat(x,y,summ); 
      edgeSums = unique(edgeSums,"rows");  
    endfor;
  endfor;
  edgeSums = sortrows(edgeSums,3);
  edgeSums(1,:)=[];
  edgeWithLowestTotal = edgeSums(rows(edgeSums)/2 + 1,:);
endfunction
