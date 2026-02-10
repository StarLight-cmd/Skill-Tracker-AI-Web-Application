import '@testing-library/jest-dom'
import { vi } from 'vitest'

/* ---- Block ALL real network requests ---- */

// block fetch
global.fetch = vi.fn(() =>
  Promise.reject(new Error("Network requests are disabled in tests"))
)

// block axios if used anywhere
vi.mock('../api/axios', () => ({
  default: {
    get: vi.fn(() => Promise.resolve({ data: {} })),
    post: vi.fn(() => Promise.resolve({ data: {} })),
    put: vi.fn(() => Promise.resolve({ data: {} })),
    delete: vi.fn(() => Promise.resolve({ data: {} })),
  }
}))
