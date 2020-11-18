import React from "react";
import MetabaseAnalytics from "metabase/lib/analytics";
import { t } from "ttag";

const EmbeddingLegalese = ({ onChange }) => (
  <button
    className="Button Button--primary"
    onClick={() => {
      MetabaseAnalytics.trackEvent(
        "Admin Embed Settings",
        "Embedding Enable Click",
      );
      onChange(true);
    }}
  >
    {t`Enable`}
  </button>
);

export default EmbeddingLegalese;
