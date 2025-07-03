#!/usr/bin/env python3
import json
import glob
from collections import OrderedDict
from typing import Union, List, Dict


def migrate_payload(data):
    cfg = data.get("config", {})
    mods = cfg.get("modules", {})
    grd = mods.get("grounding", {})
    cnf = grd.get("config", {})

    if len(cnf) > 0:
        wrapped = wrap_keys(
            data=cnf,
            wrapper="placeholders",
            keys_to_wrap=["input_params", "output_param"]
        )

        cfg["modules"]["grounding"]["config"] = wrapped

    # wrap prompt+model under prompt_templating
    # wrapped = wrap_keys(
    #     data=mods,
    #     wrapper="prompt_templating",
    #     keys_to_wrap=["prompt", "model"]
    # )
    # cfg["modules"] = wrapped

    # wrap prompt+model under placeholders
    # mods = cfg.get("modules", {})
    #
    # # drop stream
    # cfg.pop("stream", None)
    #
    # # rename input_params → placeholder_values
    # data = rename_top_level(data, "input_params", "placeholder_values")
    # # (or use your existing rename logic here)

    return data


def wrap_keys(
        data: Union[Dict, OrderedDict],
        wrapper: str,
        keys_to_wrap: List[str]
) -> OrderedDict:
    wrapped_dict = OrderedDict()
    wrapper_content = OrderedDict()  # Use an OrderedDict for the wrapper's content too

    # A flag to track if the wrapper has been added to the main dict yet
    # This ensures the wrapper key is inserted at the logical first position
    # of any of its wrapped keys, rather than at the end or some arbitrary place.
    wrapper_added = False

    for key, value in data.items():
        if key in keys_to_wrap:
            # Add the item to the wrapper's content
            wrapper_content[key] = value

            # If the wrapper hasn't been added to the main dict yet,
            # add it now, maintaining insertion order.
            if not wrapper_added:
                wrapped_dict[wrapper] = wrapper_content
                wrapper_added = True
        else:
            # If the wrapper has been added, and this key is not part of the wrapper,
            # ensure it's added after the wrapper.
            if wrapper_added:
                wrapped_dict[key] = value
            # If the wrapper has NOT been added yet (meaning we haven't encountered
            # any key to wrap), add the current key directly to the wrapped_dict.
            else:
                wrapped_dict[key] = value

    return wrapped_dict


def rename_top_level(data, old, new):
    return {
        (new if k == old else k): v
        for k, v in data.items()
    }


if __name__ == "__main__":
    for path in glob.glob("**/*.json", recursive=True):
        with open(path, "r+", encoding="utf-8") as f:
            original = json.load(f, object_pairs_hook=OrderedDict)
            migrated = migrate_payload(original)
            f.seek(0)
            json.dump(migrated, f, indent=2, ensure_ascii=False)
            f.truncate()
            print(f"  ▶ migrated {path}")
