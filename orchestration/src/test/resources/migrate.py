#!/usr/bin/env python3
import json
import glob

def migrate_payload(data):
    cfg = data.get("config", {})
    mods = cfg.get("modules", {})

    # 1. pull out existing prompt & model
    prompt = mods.pop("prompt", {})
    model  = mods.pop("model", {})

    # 2. insert new prompt_templating block
    mods["prompt_templating"] = {
        "prompt": prompt,
        "model": model
    }

    # 3. drop the old stream flag (if present)
    cfg.pop("stream", None)

    # 4. rename input_params → placeholder_values
    data["placeholder_values"] = data.pop("input_params", {})

    return data

if __name__ == "__main__":
    for path in glob.glob("**/*.json", recursive=True):
        with open(path, "r+", encoding="utf-8") as f:
            try:
                doc = json.load(f)
            except json.JSONDecodeError:
                continue
            new_doc = migrate_payload(doc)
            f.seek(0)
            json.dump(new_doc, f, indent=2, ensure_ascii=False)
            f.truncate()
            print(f"  ▶ migrated {path}")
