# find child
select child.* from nested_interval_farey node, nested_interval_farey child
where child.lft_num - rightNum(child.lft_num, child.lft_den) = node.lft_num
  and child.lft_den - rightDen(child.lft_num, child.lft_den) = node.lft_den
  and node.id = 61;

# find ancestors
select parent.* from nested_interval_farey parent, nested_interval_farey node
where
        compare(
                parent.lft_num,
                parent.lft_den,
                node.lft_num,
                node.lft_den) < 0 and
        compare(
                rightNum(node.lft_num, node.lft_den),
                rightDen(node.lft_num, node.lft_den),
                rightNum(parent.lft_num, parent.lft_den),
                rightDen(parent.lft_num, parent.lft_den)) <= 0 and
        node.id = 30;

# find descendants
select child.*, node.name from nested_interval_farey child, nested_interval_farey node
where
        compare(
                child.lft_num,
                child.lft_den,
                node.lft_num,
                node.lft_den) > 0 and
        compare(
                rightNum(node.lft_num, node.lft_den),
                rightDen(node.lft_num, node.lft_den),
                child.lft_num,
                child.lft_den) > 0 and
        node.id = 56;