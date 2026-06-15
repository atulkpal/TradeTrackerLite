---
name: Trade Journal Lite
colors:
  surface: '#031427'
  surface-dim: '#031427'
  surface-bright: '#2a3a4f'
  surface-container-lowest: '#000f21'
  surface-container-low: '#0b1c30'
  surface-container: '#102034'
  surface-container-high: '#1b2b3f'
  surface-container-highest: '#26364a'
  on-surface: '#d3e4fe'
  on-surface-variant: '#c6c6cd'
  inverse-surface: '#d3e4fe'
  inverse-on-surface: '#213145'
  outline: '#909097'
  outline-variant: '#45464d'
  surface-tint: '#bec6e0'
  primary: '#bec6e0'
  on-primary: '#283044'
  primary-container: '#0f172a'
  on-primary-container: '#798098'
  inverse-primary: '#565e74'
  secondary: '#adc6ff'
  on-secondary: '#002e6a'
  secondary-container: '#0566d9'
  on-secondary-container: '#e6ecff'
  tertiary: '#f7be1d'
  on-tertiary: '#3f2e00'
  tertiary-container: '#201600'
  on-tertiary-container: '#a27b00'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#dae2fd'
  primary-fixed-dim: '#bec6e0'
  on-primary-fixed: '#131b2e'
  on-primary-fixed-variant: '#3f465c'
  secondary-fixed: '#d8e2ff'
  secondary-fixed-dim: '#adc6ff'
  on-secondary-fixed: '#001a42'
  on-secondary-fixed-variant: '#004395'
  tertiary-fixed: '#ffdf9a'
  tertiary-fixed-dim: '#f7be1d'
  on-tertiary-fixed: '#251a00'
  on-tertiary-fixed-variant: '#5a4300'
  background: '#031427'
  on-background: '#d3e4fe'
  surface-variant: '#26364a'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 48px
    fontWeight: '700'
    lineHeight: 56px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
  headline-lg-mobile:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  title-md:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 24px
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  label-md:
    fontFamily: JetBrains Mono
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.01em
  data-lg:
    fontFamily: JetBrains Mono
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 4px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  container-margin: 16px
  gutter: 12px
---

## Brand & Style
The design system is engineered for the disciplined trader. It follows a **Modern Corporate** aesthetic heavily influenced by **Material 3** principles, prioritizing information density without sacrificing clarity. 

The brand personality is serious, methodical, and high-fidelity. It aims to evoke a sense of "professional-grade equipment"—utilitarian yet refined. The target audience includes active traders who require a reliable digital ledger to analyze performance. The UI uses a "Subtle Layering" approach, where depth is communicated through tonal shifts rather than heavy shadows, ensuring the data remains the primary focus.

## Colors
The palette is optimized for high-stakes financial environments. 

- **Primary & Backgrounds**: A deep Navy (#0F172A) serves as the core dark mode background, providing better contrast for financial charts than pure black. 
- **Accents**: Vibrant Blue (#3B82F6) is used for primary actions and system-level feedback. Premium Gold (#EAB308) is reserved for highlighting "Pro" features, streaks, or milestone achievements.
- **Semantic Colors**: Success Green and Danger Red are calibrated for accessibility, ensuring P&L (Profit and Loss) data is immediately legible.
- **Surface Tiers**: In dark mode, surfaces use incremental "lightening" of the navy base to indicate elevation. In light mode, the system shifts to a clean "Off-White" (#F8FAFC) with charcoal text.

## Typography
This design system utilizes **Inter** for all UI elements and headlines to maintain a modern, neutral, and highly readable interface. For financial data, price points, and timestamps, **JetBrains Mono** is introduced as the label font. Its monospaced nature ensures that columns of numbers align perfectly, which is critical for scanning trade logs.

- **Weight Scaling**: Bold weights are used sparingly for total balance and trade status.
- **Data Emphasis**: Use `data-lg` for large P&L displays to give them a technical, "terminal" feel.

## Layout & Spacing
The layout follows a **Fluid Grid** model based on a 4px baseline. On mobile, we use a 4-column system with 16px side margins.

- **Vertical Rhythm**: Content blocks (Cards) are separated by 12px or 16px to maintain a dense but organized feel.
- **Touch Targets**: All interactive elements (buttons, chips) maintain a minimum 48dp height/width for Material 3 compliance.
- **Safe Areas**: Critical data like the "Current Balance" is pinned to the top or contained within a persistent bottom navigation safe zone.

## Elevation & Depth
Depth is expressed through **Tonal Layers** rather than heavy drop shadows, aligning with Material 3’s "Color Mappings."

- **Level 0 (Background)**: The base navy color.
- **Level 1 (Cards/Sheet)**: A slightly lighter navy/charcoal mix.
- **Level 2 (Dialogs/Menus)**: The lightest surface color, often accompanied by a very soft, high-diffusion shadow (8% opacity) to separate it from the main deck.
- **Interaction**: On press, elements shift in tone rather than "lifting" off the page, maintaining the professional, flat aesthetic.

## Shapes
The design system uses a **Rounded** shape language (8px / 0.5rem base) to soften the "hard" data-heavy interface.

- **Small Components**: Chips and input fields use 8px corners.
- **Containers**: Cards and Bottom Sheets use `rounded-lg` (16px) to create distinct visual groupings.
- **FAB**: The Floating Action Button utilizes the Material 3 "Squircle" or a fully rounded pill shape to stand out against rectangular card layouts.

## Components

- **Buttons**: Primary buttons are high-contrast Blue with white text. Secondary buttons use a tonal navy background. "Danger" actions (Close Trade) use an outlined red style.
- **Cards**: Trade cards feature a three-section layout: Header (Asset/Date), Body (Entry/Exit data in monospaced font), and Footer (Profit/Loss tag).
- **Segmented Selectors**: Used for switching timeframes (1D, 1W, 1M, ALL). They feature a sliding background indicator for a tactile feel.
- **Floating Action Button (FAB)**: Located in the bottom right, using the Primary Blue color with a simple '+' icon for quick trade entry.
- **Charts**:
    - **Line Charts**: Use a 2px stroke width with a subtle gradient fill below the line.
    - **Bar Charts**: Use Success Green and Danger Red specifically for volume and P&L attribution.
- **Input Fields**: Outlined style with the label nested in the border. Focus state uses the Primary Blue color and a 2px border thickness.
- **Chips**: Used for "Trade Tags" (e.g., #Long, #Scalp). These are low-contrast tonal gray to avoid competing with primary P&L indicators.